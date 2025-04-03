package com.reptrack.api.exercises;

import jakarta.persistence.*;

@Entity
@Table
public class Exercise {

    @Id
    @SequenceGenerator(
            name = "exercise_sequence",
            sequenceName = "exercise_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "exercise_sequence"
    )
    private Long id;
    private String name;
    private String equipmentName;
    private String musclesTargeted;
    private String description;
    private boolean approved = false;

    public Exercise() {
    }

    public Exercise(String name,
                    Long id,
                    String equipmentName,
                    String musclesTargeted,
                    String description,
                    Boolean approved) {
        this.name = name;
        this.id = id;
        this.equipmentName = equipmentName;
        this.musclesTargeted = musclesTargeted;
        this.description = description;
        this.approved = approved;
    }

    public Exercise(String name,
                    String equipmentName,
                    String musclesTargeted,
                    String description,
                    boolean approved) {
        this.name = name;
        this.equipmentName = equipmentName;
        this.musclesTargeted = musclesTargeted;
        this.description = description;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getMusclesTargeted() {
        return musclesTargeted;
    }

    public void setMusclesTargeted(String musclesTargeted) {
        this.musclesTargeted = musclesTargeted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", musclesTargeted='" + musclesTargeted + '\'' +
                ", description='" + description + '\'' +
                ", approved=" + approved +
                '}';
    }
}
