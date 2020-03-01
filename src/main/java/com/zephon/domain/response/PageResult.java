package com.zephon.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.domain.response
 * @date 2020/2/29 下午9:35
 * @Copyright ©
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private Long total;
    private List<T> rows;
}
