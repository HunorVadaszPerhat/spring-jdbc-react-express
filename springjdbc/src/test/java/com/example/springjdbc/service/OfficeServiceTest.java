package com.example.springjdbc.service;

import com.example.springjdbc.dto.office.OfficeDTO;
import com.example.springjdbc.mapper.office.OfficeMapper;
import com.example.springjdbc.model.office.Office;
import com.example.springjdbc.repository.office.OfficeRepository;
import com.example.springjdbc.service.office.OfficeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OfficeServiceTest {

    @Mock
    private OfficeRepository repository;

    @Mock
    private OfficeMapper mapper;

    @InjectMocks
    private OfficeService service;

    private Office office;
    private OfficeDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        office = new Office("1", "Copenhagen", "123456", "Main St", null, null, "Denmark", "2100", "EU");
        dto = new OfficeDTO("1", "Copenhagen", "123456", "Main St", null, null, "Denmark", "2100", "EU");
    }

    @Test
    void testGetAllOffices() {
        when(repository.findAll()).thenReturn(List.of(office));
        when(mapper.toDTO(office)).thenReturn(dto);
        List<OfficeDTO> result = service.getAllOffices();
        assertEquals(1, result.size());
    }

    @Test
    void testGetOfficeById() {
        when(repository.findById("1")).thenReturn(Optional.of(office));
        when(mapper.toDTO(office)).thenReturn(dto);
        OfficeDTO result = service.getOfficeById("1");
        assertEquals("Copenhagen", result.getCity());
    }

    @Test
    void testCreateOffice() {
        when(mapper.toEntity(dto)).thenReturn(office);
        service.createOffice(dto);
        verify(repository).save(office);
    }

    @Test
    void testUpdateOffice() {
        when(mapper.toEntity(dto)).thenReturn(office);
        service.updateOffice(dto);
        verify(repository).update(office);
    }

    @Test
    void testDeleteOffice() {
        service.deleteOffice("1");
        verify(repository).delete("1");
    }
}