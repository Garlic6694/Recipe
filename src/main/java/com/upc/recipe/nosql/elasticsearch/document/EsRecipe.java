package com.upc.recipe.nosql.elasticsearch.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Document(indexName = "xiafannew")
public class EsRecipe implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private Integer id;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String title;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String des;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String cats;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String ing;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String steps;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String tip;

    private Integer status;

}
