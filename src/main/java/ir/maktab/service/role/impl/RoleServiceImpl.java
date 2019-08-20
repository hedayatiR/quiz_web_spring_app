package ir.maktab.service.role.impl;

import ir.maktab.mapper.RoleMapper;
import ir.maktab.model.role.Role;
import ir.maktab.model.role.dto.RoleDto;
import ir.maktab.repository.RoleRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.role.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleDto, Long, RoleRepository, RoleMapper>
    implements RoleService {

    public RoleServiceImpl(RoleRepository repository, RoleMapper baseMapper) {
        super(repository, baseMapper);
    }

}
