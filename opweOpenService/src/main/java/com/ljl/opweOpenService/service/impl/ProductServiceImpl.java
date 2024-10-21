package com.ljl.opweOpenService.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljl.opweOpenService.dao.ProductMapper;
import com.ljl.opweOpenService.dao.ProductTagMapper;
import com.ljl.opweOpenService.dao.StatusMapper;
import com.ljl.opweOpenService.entity.constants.ProductExceptionConst;
import com.ljl.opweOpenService.entity.dtos.ProductResponseDto;
import com.ljl.opweOpenService.entity.dtos.ProductResultListDto;
import com.ljl.opweOpenService.entity.dtos.ProductWithImgDto;
import com.ljl.opweOpenService.entity.enums.StatusGroupEnum;
import com.ljl.opweOpenService.entity.pos.ProductPo;
import com.ljl.opweOpenService.entity.pos.ProductTagPo;
import com.ljl.opweOpenService.exceptions.GeneralException;
import com.ljl.opweOpenService.service.ProductService;
import com.ljl.opweOpenService.utils.SnowflakeUtil;
import io.minio.ObjectWriteResponse;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/6/1 21:45
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName ProductServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class ProductServiceImpl implements ProductService {
    private ProductMapper productMapper;

    private SnowflakeUtil snowflakeUtil;

    private MinioServiceImpl minioService;

    private StatusMapper statusMapper;

    private ProductTagMapper productTagMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper,
                              SnowflakeUtil snowflakeUtil,
                              MinioServiceImpl minioService,
                              StatusMapper statusMapper,
                              ProductTagMapper productTagMapper){
        this.productMapper = productMapper;
        this.snowflakeUtil = snowflakeUtil;
        this.minioService = minioService;
        this.statusMapper = statusMapper;
        this.productTagMapper = productTagMapper;
    }

    @Override
    public void insertSingleProduct(ProductPo productPo) {
        productPo.setProductId(snowflakeUtil.getNextId());
        productMapper.insertSingleProduct(productPo);
    }

    @Override
    public void createNewProductWithImg(ProductWithImgDto productWithTagsDto, List<MultipartFile> productImgs) throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        Long productId = snowflakeUtil.getNextId();
        String bucketName = "product-imgs";
        String fileName = "File" + productId;
        minioService.ensureBucketExists(bucketName);
        ObjectWriteResponse objectWriteResponse = minioService.uploadFileSimple(bucketName, fileName, productImgs.get(0));
        if(objectWriteResponse == null){
            throw new RuntimeException("Upload file failed");
        }
        productMapper.insertSingleProduct(new ProductPo(
                productId,
                productWithTagsDto.getProductName(),
                productWithTagsDto.getProductDesc(),
                fileName,
                statusMapper.queryStatusByGroupAndName(StatusGroupEnum.ProductGroupStatus.getStatusGroup(), "available"),
                productWithTagsDto.getProductPrice(),
                productWithTagsDto.getProductStock(),
                0,
                null
        ));
        for(Long tagId: productWithTagsDto.getProductTags()){
            productTagMapper.insertProductTag(new ProductTagPo(
                    snowflakeUtil.getNextId(),
                    productId,
                    tagId
            ));
        }

    }

    @Override
    public List<ProductPo> queryAllProduct() {
        return productMapper.queryAllProduct();
    }

    @Override
    public ProductPo queryProductStockAndOnHold(Long productId) {
        ProductPo result = productMapper.queryProductStockAndOnHold(productId);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int processProductPreOrder(Long productId, int amount) throws GeneralException {
        //TODO check the logic
        ProductPo postProduct = productMapper.queryProductStockAndOnHold(productId);
        if(postProduct.getProductOnHold() + amount > postProduct.getProductStock()){
            throw new GeneralException(ProductExceptionConst.ExceedAmountException, null, "Exceed product amount limit.");
        }
        productMapper.updateProductOnHold(productId, postProduct.getProductOnHold() + amount);
        return 0;
    }

    @Override
    public ProductResponseDto getProductWithImg(Long productId){
        try{
            ProductResponseDto productResponseDto = productMapper.queryProductById(productId);
            String imageUrl = minioService.generatePresignedUrl("product-imgs", productResponseDto.getProductImageRoute(), 30);
//            InputStream inputStream = minioService.fetchFile("product-imgs", productResponseDto.getProductImageRoute());
//            byte[] content = inputStream.readAllBytes();
//            inputStream.close();
//            productResponseDto.setProductImage(Base64.getEncoder().encodeToString(content));
            productResponseDto.setProductImage(imageUrl);
            return productResponseDto;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public ProductResultListDto getProductListWithParams(String searchParams, Integer pageSize, Integer pageOffset){
        List<Long> productIdList = productMapper.queryProductIdList(searchParams, pageSize, pageOffset);
        if(productIdList.size() == 0){
            return new ProductResultListDto(null, 0);
        }
        List<ProductResponseDto> basicResult = productMapper.queryProductList(productIdList);
        for(ProductResponseDto productResponseDto : basicResult){
            productResponseDto.setProductImage(productResponseDto.getProductImageRoute() != null ? minioService.generatePresignedUrl("product-imgs", productResponseDto.getProductImageRoute(), 180) : null);
        }
        Integer count = productMapper.countProductWithSearchParam(searchParams);
        return new ProductResultListDto(basicResult, count);
    }
}
