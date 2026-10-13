package be.pxl.organizationservice.repository;

import be.pxl.organizationservice.model.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Testcontainers
class OrganizationRepositoryTest {

    @Container
    @ServiceConnection
    static MySQLContainer mysql = new MySQLContainer("mysql:8.4");

    @Autowired
    private OrganizationRepository organizationRepository;

    @BeforeEach
    void cleanDatabase() {
        organizationRepository.deleteAll();
    }

    private Organization createOrganization(String name) {
        Organization organization = new Organization();
        organization.setName(name);
        return organization;
    }

    @Test
    void savesAndFindsOrganization() {
        Organization saved = organizationRepository.save(createOrganization("PXL"));

        Organization found = organizationRepository.findById(saved.getId()).orElseThrow();

        assertEquals("PXL", found.getName());
    }

    @Test
    void deleteById_removesOrganization() {
        Organization saved = organizationRepository.save(createOrganization("PXL"));
        organizationRepository.save(createOrganization("UHasselt"));

        organizationRepository.deleteById(saved.getId());

        assertFalse(organizationRepository.existsById(saved.getId()));
        assertEquals(1, organizationRepository.count());
    }
}