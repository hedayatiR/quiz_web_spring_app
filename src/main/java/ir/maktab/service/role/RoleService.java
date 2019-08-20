package ir.maktab.service.role;

import ir.maktab.mapper.RoleMapper;
import ir.maktab.model.role.Role;
import ir.maktab.model.role.dto.RoleDto;
import ir.maktab.service.base.BaseService;

public interface RoleService extends BaseService<Role, RoleDto, Long, RoleMapper> {
}
