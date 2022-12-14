package com.bjpowernode.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.bjpowernode.common.consts.AppConstants;
import com.bjpowernode.db.domain.ProductInfoDO;
import com.bjpowernode.web.service.ProductsService;
import com.bjpowernode.web.struct.CommonResult;
import com.bjpowernode.web.struct.PageInfo;
import com.bjpowernode.web.struct.dto.ThreeTypeProductsDTO;
import com.bjpowernode.web.struct.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjunchen
 */
@Api(tags = "理财产品模块")
@RestController
public class ProductController {
    @Resource
    private ProductsService productsService;

    /**
     * 获取三项产品中展示数据：新手宝，优选，散标
     *
     * @return commonResult封装的产品信息
     */
    @ApiOperation(value = "查询产品信息", notes = "一个新手宝，三个优选，三个散标，优选和散标按照发布时间和利率倒序排序")
    @RequestMapping("/products/three")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功", response = CommonResult.class),
            @ApiResponse(code = 401, message = "不能访问系统"),
            @ApiResponse(code = 403, message = "权限不足"),
            @ApiResponse(code = 404, message = "找不到服务器"),
            @ApiResponse(code = 500, message = "服务器错误，请联系张三")
    })
    public CommonResult getThreeProducts() {

        ThreeTypeProductsDTO threeTypeProductsDTO = productsService.queryPageByProductType();

        /*
         * DTO转换为VO
         * DTO中的数据都是集合，而VO中的数据是字段属性，因此不能使用copyProperties直接复制
         * 而是使用hutool工具库中BeanUtil.copyToList方法
         * 此方法可以复制集合中的Bean属性，遍历集合中每个Bean，复制其对应属性后加入一个新的List中。
         * 形参:collection – 原Bean集合 targetType – 目标Bean类型
         * 返回值:复制后的List
         */
        List<ProductVO> newList = BeanUtil.copyToList(threeTypeProductsDTO.getNewList(), ProductVO.class);
        List<ProductVO> goodList = BeanUtil.copyToList(threeTypeProductsDTO.getGoodList(), ProductVO.class);
        List<ProductVO> bulkList = BeanUtil.copyToList(threeTypeProductsDTO.getBulkList(), ProductVO.class);

        //以上获得了三组集合数据，将其添加到map集合中，然后封装到CommonResult
        Map<String, Object> data = new HashMap<>(20);
        data.put("newList", newList);
        data.put("goodList", goodList);
        data.put("bulkList", bulkList);
        return CommonResult.success(data);
    }

    /**
     * 分页查询优选和散标类理财产品
     *
     * @param productType 产品类型
     * @param pageNo      分页，即第几页数据
     * @return commonResult封装的产品类型和分页信息
     */
    @ApiOperation(value = "产找产品类型分页查询", notes = "分页查询优选和散标类理财产品")
    @RequestMapping("/product/type")
    public CommonResult queryPageByType(@RequestParam Integer productType,
                                        @RequestParam Integer pageNo) {
        CommonResult commonResult = CommonResult.failure();
        if ((productType != null && productType > 0)) {
            //页数为空或者页数小于1时，默认从1开始
            pageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
            List<ProductVO> productVOList = new ArrayList<>();
            //某个类型产品总记录数量
            Long counts = productsService.countByProductType(productType);
            if (counts > 0) {
                List<ProductInfoDO> productInfoDOList = productsService.findPageByProductType(productType, pageNo);
                productVOList = BeanUtil.copyToList(productInfoDOList, ProductVO.class);
            }
            //创建pageInfo
            PageInfo pageInfo = new PageInfo(pageNo, AppConstants.PAGE_SIZE, counts);
            //封装结果
            Map<String, Object> data = new HashMap<>(20);
            data.put("productList", productVOList);
            data.put("pageInfo", pageInfo);
            commonResult = CommonResult.success(data);
        }
        return commonResult;
    }

    @ApiOperation(value = "")
    @GetMapping
    public CommonResult getProductDetail(@RequestParam Integer pid,
                                         @RequestHeader(required = false) Integer uid) {
        CommonResult commonResult = CommonResult.failure();
        

        return commonResult;
    }
}
