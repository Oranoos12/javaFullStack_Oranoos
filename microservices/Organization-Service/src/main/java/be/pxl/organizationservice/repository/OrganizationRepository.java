package be.pxl.organizationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.pxl.organizationservice.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
