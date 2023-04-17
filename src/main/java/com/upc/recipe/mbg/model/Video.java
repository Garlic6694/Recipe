package com.upc.recipe.mbg.model;

import java.io.Serializable;
import lombok.Data;

/**
 * video
 * @author canwe
 * @date 2023-04-12 19:42:51
 */
@Data
public class Video implements Serializable {
    /**
     */
    private Integer id;

    /**
     */
    private Integer likeCount;

    /**
     */
    private Integer scanCount;

    /**
     */
    private Integer comCount;

    /**
     */
    private String title;

    /**
     */
    private String url;

    /**
     */
    private String imgurl;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", scanCount=").append(scanCount);
        sb.append(", comCount=").append(comCount);
        sb.append(", title=").append(title);
        sb.append(", url=").append(url);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}