package com.kimp.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KimpParam {

    @ApiModelProperty(value="코인 약어", notes="", example="", required=false)
    private String coin;

}


