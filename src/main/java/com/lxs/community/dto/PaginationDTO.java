package com.lxs.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/9 - 13:07
 */
@Data
public class PaginationDTO {

    private List<QuestionDTO> questions;

    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    private Integer page;

    private Integer totalPage; //总共需要的页数

    private List<Integer> pages = new ArrayList<>();

    //计算分页显示的图标
    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        this.page = page;

        //计算pages
        pages.add(page);
        for(int i = 1; i <= 3; i++){
            if(page - i > 0 ){
                pages.add(0 , page-i);
            }
            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }


        //是否显示上一页
        if(page == 1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }

        //是否显示下一页
        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }

        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }

        //是否展示第一页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
    }
}
