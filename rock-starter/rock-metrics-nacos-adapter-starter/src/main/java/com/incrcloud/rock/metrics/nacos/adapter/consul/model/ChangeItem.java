package com.incrcloud.rock.metrics.nacos.adapter.consul.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * describe: Contains "something" with its last changed index
 *
 * @author sirk
 * @date 2020/11/13
 */
@Getter
@AllArgsConstructor
@ToString
public class ChangeItem<T> {

	private T item;

	private long changeIndex;

}