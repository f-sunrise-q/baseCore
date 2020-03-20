package com.advance.reponse;

import com.advance.i18n.I18nUtils;

/**
 * web接口响应快速构造工具
 *
 * @author: fanqin
 * @Date: 2019/6/1 15:01
 */
public class BaseResponseBuilder {

    public static BaseResponse createResponse() {
        return createResponse(DefaultErrorCode.SUCCESS);
    }

    public static <T> BaseResponse<T> createResponse(T data) {
        BaseResponse<T> response = createResponse(DefaultErrorCode.SUCCESS);
        response.setData(data);
        return response;
    }

//    /**
//     * 构造列表数据响应体
//     *
//     * @param data
//     * @param <T>
//     * @return
//     */
//    public static <T> BaseResponse<ListData<T>> createListResponse(List<T> data) {
//        BaseResponse<ListData<T>> response = createResponse(DefaultErrorCode.SUCCESS);
//        ListData<T> listData = new ListData<>();
//        if (data == null) {
//            data = new ArrayList<>();
//        }
//        listData.setList(data);
//        listData.setTotal((long) data.size());
//        response.setData(listData);
//        return response;
//    }
//
//    /**
//     * 构造分页数据响应体
//     *
//     * @param page
//     * @param <T>
//     * @return
//     */
//    public static <T, U> BaseResponse<PageData<T>> createPageResponse(Page<U> page, DataConverter<U, T> dataConverter) {
//        List<T> result = new ArrayList<T>((int) page.getSize());
//        if (ObjectUtils.isNotEmpty(page.getRecords())) {
//            page.getRecords().forEach(record -> {
//                result.add(dataConverter.convert(record));
//            });
//        }
//        BaseResponse<PageData<T>> response = getPageDataBaseResponse(page, result);
//        return response;
//    }
//
//    /**
//     * 构造分页数据响应体
//     *
//     * @param page
//     * @param <T>
//     * @return
//     */
//    public static <T> BaseResponse<PageData<T>> createPageResponse(Page<T> page) {
//
//        BaseResponse<PageData<T>> response = getPageDataBaseResponse(page, page.getRecords());
//        return response;
//    }

    /**
     * 根据IErrorCode构造响应
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse createResponse(IErrorCode errorCode) {
        BaseResponse response = new BaseResponse();
        String code = errorCode.getCode();
        response.setCode(code);
        response.setMsg(I18nUtils.getMessage(errorCode.getMessage()));
        return response;
    }

    public static BaseResponse createResponse(IErrorCode errorCode, String msgKey, Object... params) {
        return createResponse(errorCode.getCode(), msgKey, params);
    }

    public static <T> BaseResponse<T> createResponse(String code, String msg, Object... params) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(code);
        response.setMsg(I18nUtils.getMessage(msg, params));
        return response;
    }
//
//    private static <T> BaseResponse<PageData<T>> getPageDataBaseResponse(Page page, List<T> pageList) {
//        PageData<T> pageData = new PageData<>();
//        pageData.setTotal(page.getTotal());
//        pageData.setList(pageList);
//        pageData.setPageNo(page.getCurrent());
//        pageData.setPageSize(page.getSize());
//        pageData.setTotalPage(page.getPages());
//
//        BaseResponse<PageData<T>> response = createResponse(DefaultErrorCode.SUCCESS);
//        response.setData(pageData);
//        return response;
//    }

}
