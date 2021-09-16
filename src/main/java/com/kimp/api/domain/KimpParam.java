package com.kimp.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KimpParam {

    @ApiModelProperty(value="사이트 이름", notes="", example="", required=false)
    private String Site;

}


