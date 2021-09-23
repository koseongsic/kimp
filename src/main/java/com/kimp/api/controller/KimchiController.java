package com.kimp.api.controller;

import com.kimp.api.domain.KimpParam;
import com.kimp.api.service.BybitService;
import com.kimp.api.service.RateService;
import com.kimp.api.service.UpbitService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class KimchiController {

    @Autowired
    BybitService bybitService;
    @Autowired
    UpbitService upbitService;
    @Autowired
    RateService rateService;
    @Value("${kimp.coin.list}")
    private List<String> coins;
    @ApiOperation(value = "kimp parameters", response = KimpParam.class)
    @ApiResponses(@ApiResponse(code = 200, message = "김프 조회 성공"))
    @GetMapping(value = "/kimp/bybit")
    public ResponseEntity<?> kimpSearch() throws Exception {
        Double rate = rateService.getRate();
        Map<String,Map<String,String>> returnMap = new HashMap<>();
        Map<String, Double> bybitServiceList = bybitService.getList(coins);
        Map<String, Double> upbitServiceList = upbitService.getList(coins);

        for (String coin : coins) {
            Map<String,String> kimpMap = new HashMap<>();
            Double upbitPrice = upbitServiceList.get(coin);
            Double bybitPrice = bybitServiceList.get(coin);
            Double kimpPrice = ((upbitPrice/(bybitPrice*rate))-1)*100;
            String kimp = String.format("%.2f", kimpPrice);
            kimpMap.put("김치프리미엄",kimp+"%");
            kimpMap.put("업비트 가격",String.format("%.2f", upbitPrice));
            kimpMap.put("바이비트 가격",String.format("%.2f", bybitPrice*rate));
            returnMap.put(coin,kimpMap);
        }
        return ResponseEntity.ok(returnMap);
    }
}
