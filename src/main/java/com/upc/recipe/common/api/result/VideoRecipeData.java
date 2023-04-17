package com.upc.recipe.common.api.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VideoRecipeData<T> {
    int size;
    boolean success;
    List<T> data;
}
