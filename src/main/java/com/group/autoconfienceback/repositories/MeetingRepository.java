package com.group.autoconfienceback.repositories;

import com.group.autoconfienceback.entities.user_entities.Client;
import com.group.autoconfienceback.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    public List<Meeting> findByYearAndMonthAndDay(int year, int month, int day);
    public List<Meeting> findByConfirmed(int confirmed);
    public List<Meeting> findByClient(Client client);
}
