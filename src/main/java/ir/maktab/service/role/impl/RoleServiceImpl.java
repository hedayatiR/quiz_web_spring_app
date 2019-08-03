package ir.maktab.service.role.impl;

import ir.maktab.model.role.Role;
import ir.maktab.repository.RoleRepository;
import ir.maktab.service.base.impl.BaseServiceImpl;
import ir.maktab.service.role.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, Long, RoleRepository>
    implements RoleService {

    public RoleServiceImpl(RoleRepository baseRepository) {
        super(baseRepository);
    }
}
