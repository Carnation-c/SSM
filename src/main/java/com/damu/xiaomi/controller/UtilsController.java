package com.damu.xiaomi.controller;

import com.damu.xiaomi.entry.Goods;
import com.damu.xiaomi.entry.GoodsType;
import com.damu.xiaomi.service.GoodsShippingService;
import com.damu.xiaomi.service.GoodsTypeService;
import com.damu.xiaomi.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/u")
public class UtilsController {
    @Autowired
    private GoodsShippingService goodsShippingService;
    @Autowired
    private GoodsTypeService goodsTypeService;

    @GetMapping("/search/{name}")
    @ResponseBody
    public ResponseMessage searchGoodsWithName(@PathVariable String name){
        List<Goods> goodsList = goodsShippingService.searchGoodsWithName(name);
        return goodsList.size() > 0 ? ResponseMessage.success().addObject("goodsList",goodsList)
                                    : ResponseMessage.error();
    }

    @GetMapping("/search/{level}/{goodsTypeId}")
    @ResponseBody
    public ResponseMessage searchGoodsWithType(@PathVariable String level,@PathVariable String goodsTypeId){
        GoodsType goodsType = goodsTypeService.findById(Integer.parseInt(goodsTypeId));
        List<Goods> goodsList = null;
        if ("1".equals(level)){
            goodsList = goodsShippingService.findGoodsWithToptype(goodsType);
        }else if ("2".equals(level)){
            goodsList = goodsShippingService.findGoodsWithType(goodsType);
        }
        return goodsList != null && goodsList.size() > 0
                ? ResponseMessage.success().addObject("goodsList",goodsList)
                : ResponseMessage.error();
    }
}
