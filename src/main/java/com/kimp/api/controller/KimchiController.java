package com.kimp.api.controller;

import com.kimp.api.domain.KimpParam;
import com.kimp.api.service.BybitService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class KimchiController {

    @Autowired
    BybitService bybitService;

    @ApiOperation(value="kimp parameters", response= KimpParam.class)
    @ApiResponses(@ApiResponse(code=200, message="김프 조회 성공"))
    @GetMapping(value = "/kimp")
    public ResponseEntity<?> kimpSearch(KimpParam kimpParam) throws Exception {
        bybitService.getList();
        return ResponseEntity.ok("");
    }
}
