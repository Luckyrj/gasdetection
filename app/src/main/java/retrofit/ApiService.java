package retrofit;

import model.BrandSpec;
import model.CheckItems;
import model.CheckItemsDetail;
import model.CheckRecord;
import model.CheckResult;
import model.CheckTask;
import model.HistoricalData;
import model.LaborFeeData;
import model.Street;
import model.SystemUser;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {
    /*
     * 用户登录
     * */
    @GET("IsLogin")
    Observable<BaseModel<SystemUser>> login(
            @Query("user_name") String username,
            @Query("user_pwd") String password,
            @Query("device_mac") String device_mac,
            @Query("device_type") String device_type
    );


    /*
    * 修改密码
    * */
    @GET("UpdatePwd")
    Observable<BaseModel<SystemUser>> updatePwd(
            @Query("user_name") String username,
            @Query("old_pwd") String old_pwd,
            @Query("new_pwd") String new_pwd
    );

    /*
  *  街道小区
  * */
    @GET("GetStreetAndArea")
    Observable<BaseModel<Street>> getStreetAndArea(
            @Query("assign_to") int id,
            @Query("token") boolean token
    );


    /*
     *员工安检任务分页获取(默认一页10条)
    * */
    @GET("CheckTaskByPage")
    Observable<BaseModel<CheckTask>> checkTaskList(
            @Query("page") int page,
            @Query("assign_to") int assign_to,
            @Query("token") boolean token,
            @Query("street") String street,
            @Query("area") String area,
            @Query("key") String key,
            @Query("status") String status
    );

    /*
   * 用post方法增加一条安检记录
   * */
    @FormUrlEncoded
    @POST("AddCheckData")
    Observable<BaseModel<CheckRecord>> addCheckData(
            @Field("task_id") int task_id,
            @Field("check_datail") String check_datail,
            @Field("base64string") String base64string,
            @Field("fileName") String fileName
    );

    /*
   *  员工安检项目
   * */
    @GET("CheckProjectByPage")
    Observable<BaseModel<CheckItems>> checkProjectList(
            @Query("assign_to") int id
    );

    /*
   *  员工安检项目条目
   * */
    @GET("CheckProjectItemByPage")
    Observable<BaseModel<CheckItemsDetail>> checkProjectItemList(
            @Query("assign_to") int id,
            @Query("project_id") int project_id
    );

    /*
   *   产品品牌和型号
   * */
    @GET("GetBrandAndSpecification")
    Observable<BaseModel<BrandSpec>> getBrandAndSpec(
            @Query("assign_to") int id,
            @Query("type") String type,
            @Query("type1") String type1
    );


    /*
  * 用post方法提交检查条目
  * */
    @FormUrlEncoded
    @POST("CheckItemCommit")
    Observable<BaseModel<CheckRecord>> checkItemCommit(
            @Field("check_data_id") int check_data_id,
            @Field("project_id") int project_id,
            @Field("brand") String brand,
            @Field("specification") String specification,
            @Field("use_year") float use_year,
            @Field("size") String size,
            @Field("unqualified") String unqualified,
            @Field("remark") String remark,
            @Field("video") String video,
            @Field("video_name") String video_name,
            @Field("picture1") String picture1,
            @Field("picture1_name") String picture1_name,
            @Field("picture2") String picture2,
            @Field("picture2_name") String picture2_name,
            @Field("picture3") String picture3,
            @Field("picture3_name") String picture3_name
    );

    /*
     * 燃气安全知识宣讲提交
     * */
    @GET("PublicityCommit")
    Observable<BaseModel<SystemUser>> publicityCommit(
            @Query("check_data_id") int check_data_id,
            @Query("publicity") String publicity
    );

    /*
   * 获得安检结果
   * */
    @GET("GetCheckResult")
    Observable<BaseModel<CheckResult>> getCheckResult(
            @Query("check_data_id") int check_data_id
    );

    /*
     *  用post方法确认签字提交
     * */
    @FormUrlEncoded
    @POST("ConfirmSignCommit")
    Observable<BaseModel<CheckRecord>> confirmSignCommit(
            @Field("check_data_id") int check_data_id,
            @Field("sign_picture") String sign_picture,
            @Field("sign_picture_name") String sign_picture_name,
            @Field("is_charge") boolean is_charge,
            @Field("check_result") String check_result
    );

    /*
  *  用post方法拒绝入户
  * */
    @FormUrlEncoded
    @POST("Reject")
    Observable<BaseModel<CheckRecord>> reject(
            @Field("check_data_id") int check_data_id,
            @Field("check_result") String check_result,
            @Field("remark") String remark,
            @Field("miss_time") String miss_time,
            @Field("picture1") String picture1,
            @Field("picture1_name") String picture1_name
    );

    /*
*  用post方法到访不遇
* */
    @FormUrlEncoded
    @POST("Miss")
    Observable<BaseModel<CheckRecord>> miss(
            @Field("check_data_id") int check_data_id,
            @Field("check_result") String check_result,
            @Field("remark") String remark,
            @Field("miss_time") String miss_time,
            @Field("picture1") String picture1,
            @Field("picture1_name") String picture1_name
    );


    /*
     *  (安检)客户历史数据(默认一页10条)
     * */
    @GET("CustomerHistoryByPage")
    Observable<BaseModel<HistoricalData>> checkHistory(
            @Query("page") int page,
            @Query("assign_to") int assign_to,
            @Query("customer_id") int customer_id
    );

    /*
   *  风险等级备注提交
   * */
    @GET("RiskRemarkCommit")
    Observable<BaseModel<CheckRecord>> riskRemarkCommit(
            @Query("check_data_id") int check_data_id,
            @Query("remark") String remark
    );

    /*
   *  获取员工点火任务下的客户街道和小区
   * */
    @GET("GetStreetAndArea2")
    Observable<BaseModel<Street>> getStreetAndArea2(
            @Query("assign_to") int id,
            @Query("token") boolean token
    );

    /*
     *员工点火任务分页获取(默认一页10条)
    * */
    @GET("VentilateTaskByPage")
    Observable<BaseModel<CheckTask>> ventilateTaskList(
            @Query("page") int page,
            @Query("assign_to") int assign_to,
            @Query("token") boolean token,
            @Query("street") String street,
            @Query("area") String area,
            @Query("key") String key,
            @Query("status") String status
    );

    /*
     *  员工人工服务费用条目(默认一页10条)
     * */
    @GET("LaborItemByPage")
    Observable<BaseModel<LaborFeeData>> laborFeeList(
            @Query("page") int page,
            @Query("assign_to") int assign_to
    );

    /*
   *  材料费用条目(默认一页10条)
   * */
    @GET("MaterialItemByPage")
    Observable<BaseModel<LaborFeeData>> materialFeeList(
            @Query("page") int page,
            @Query("assign_to") int assign_to
    );

    /*
   *  器具费用条目(默认一页10条)
   * */
    @GET("ApplianceItemByPage")
    Observable<BaseModel<LaborFeeData>> applianceFeeList(
            @Query("page") int page,
            @Query("assign_to") int assign_to
    );
}

