package com.group.autoconfienceback.repositories;

import com.group.autoconfienceback.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    public List<Meeting> findByYearAndMonthAndDay(int year, int month, int day);
}
