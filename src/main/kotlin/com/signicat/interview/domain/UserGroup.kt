package com.signicat.interview.domain


import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.signicat.interview.domain.dto.UserGroupDto
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "user_group")
data class UserGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @field:NotBlank
    val name: String
) {

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    @JsonIgnoreProperties(value = ["groups"])
    val subject: Set<Subject> = emptySet()

    fun mapToDto(): UserGroupDto {
        return UserGroupDto(this.id, this.name)
    }
}
