package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.AdminDTO;
import lk.ijse.glowcare_backend.dto.AdminDashboardDTO;

import java.util.List;

public interface AdminService {

    AdminDashboardDTO getNexusStats();
    void clearSystemCache();

//    Management Methods

    List<AdminDTO> getAllAdmins();
    void updateAdmin(AdminDTO adminDTO);
    void deleteAdmin(Long id);
}
