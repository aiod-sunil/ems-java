package com.aiod.ems.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	@Id
	private String id;
	private String name;
	private String type;
	private String status;//open/wip/closed
	private Date createdDate;

}
