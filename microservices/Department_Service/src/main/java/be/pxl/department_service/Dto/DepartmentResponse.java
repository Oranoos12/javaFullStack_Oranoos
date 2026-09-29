package be.pxl.department_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class DepartmentResponse {
    private Long id;
    private String name;
    private Long organizationId;

}



