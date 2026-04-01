package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.RoutineConflictRequestDTO;
import lk.ijse.glowcare_backend.dto.RoutineConflictResponseDTO;

public interface SmartEngineService {
    RoutineConflictResponseDTO checkConflict(RoutineConflictRequestDTO request);
}
