package org.abdelhafeez.dr_momen.repo;

import java.util.List;

import org.abdelhafeez.dr_momen.entity.Patient;
import org.abdelhafeez.dr_momen.enums.AgeUnit;
import org.abdelhafeez.dr_momen.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {

    List<Patient> findAllByName(String name);

    Page<Patient> findByNameContaining(String name, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name = :name, p.phone = :phone, p.age = :age, p.ageUnit = :ageUnit, p.gender = :gender, p.moreInfo = :moreInfo, p.address = :address WHERE p.id = :id")
    void updatePatient(@Param("id") Long id, @Param("name") String name, @Param("phone") String phone,
            @Param("age") Integer age, @Param("ageUnit") AgeUnit ageUnit, @Param("gender") Gender gender,
            @Param("moreInfo") String moreInfo, @Param("address") String address);

}
