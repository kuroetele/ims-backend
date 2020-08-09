package com.kuro.ims.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T>
{
     T data;
}
