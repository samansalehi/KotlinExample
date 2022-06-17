package com.signicat.interview.domain


import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.signicat.interview.domain.dto.SubjectDto
import com.signicat.interview.domain.dto.UserGroupDto
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    var id: Long,
    @field:NotBlank
    val username: String,
    var password: String,
) {
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "subject_user_group",
        joinColumns = [JoinColumn(name = "subject_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_group_id", referencedColumnName = "id")]
    )
    @JsonIgnoreProperties(value = ["subject"])
    val groups: Set<UserGroup> = emptySet()

    fun mapToDto(): SubjectDto {
        return SubjectDto(this.id,this.username
            ,this.groups.map{u-> UserGroupDto(u.id,u.name) }.toSet())
    }
}
