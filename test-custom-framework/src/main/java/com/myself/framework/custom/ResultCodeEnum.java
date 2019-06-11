package com.myself.framework.custom;

/**
 * 类描述：返回码
 *
 * @author 王洁
 */
public enum ResultCodeEnum {

    success("exchange请求成功","10110000"),
    EXCHANGE_REQUEST_TIMEOUT("exchange请求超时","99999999"),
    exchangeRequestFail("exchange请求失败","10110001"),
    shipInfoIsNull("船信息为空","10110002"),
    shipNameIsNull("船名为空","10110003"),
    shipTypeIsIllegal("提单类型不合法","10110004"),
    addShipFail("增加船舶信息失败","10110005"),
    shipTypeIsNull("提单类型为空","10110006"),
    numCustomerIsNull("未输入提单号，不能保存！","10110007"),
    houseIsNull("仓库为空","10110007"),
    goodsIdIsNull("产品ID为空","10110008"),
    goodsNumOutIsNull("提货数量为空","10110009"),
    planDateIsNull("提单开始日期为空","10110010"),
    expireDateIsNull("提单有效期至为空","10110011"),
    locationIsNull("罐号为空","10110012"),
    memoIsTooLang("备注过长","10110013"),
    goodsNumOutIsNotLegalNum("提货数量不是合法数字","10110014"),
    userIdIsNull("userId为空","10110015"),
    userNameIsNull("userName为空","10110016"),
    ownerIdIsNull("企业id为空","10110017"),
    ownerNameIsNull("企业名称为空","10110018"),
    planDateIsBeforeExpire("提单开始日期大于有效期至","10110019"),
    planDateIsBeforeNow("提单开始日期小于当前时间","10110020"),
    expireDateIsBeforeNow("提单有效期至小于当前时间","10110021"),
    numCustomerIsExists("提单号重复，请检查后再保存！","10110022"),
    goodsInfoIsNull("货品信息不存在","10110023"),
    customerIsStop("已经停用，不允许将货物转移至该客户，如有需要请与我司联系！","10110024"),
    batchIsNotExist("批次信息不存在","10110025"),
    batchListIsNull("批次信息为空","10110026"),
    curQuantityLessThanZero("本次转卖数量小于0","10110027"),
    curQuantityMoreThanMaxQuantity("本次转卖数量大于最大可转数量","10110028"),
    maxQuantityIsNotExistOrIsZero("对应的最大可转数量为0或不存在","10110029"),
    siteIdIsNull("siteId为空","10110030"),
    createBillFail("制单失败","10110031"),
    goodsNumAndBatchesTotalNumIsNotEqual("提货数量和选择批次总量不等","10110032"),
    wmsRequestFail("操作数据失败","10110032"),
    insertStateFail("插入提货单状态失败","10110033"),
    locationIsError("罐号信息有误","10110034"),
    billInfoIsNull("提单信息为空","10110035"),
    billIdIsNull("仓库提单id为空","10110036"),
    shipMobileIsNull("船方联系人为空","10110037"),
    arrivalDateIsNull("预计到港日期为空","10110038"),
    arrivalDateBeforeNow("预计到港日期小于当前日期","10110039"),
    billOutInfoIsNotExist("提货单信息不存在","10110040"),
    billStateIsNotCreate("此单据之前做过业务处理(不是新制单据)不能进行复核确认","10110041"),
    updateBillStateFail("更新提单状态失败","10110042"),
    SYNCHRONIZE_TO_WMS_FAIL("wms同步订单状态失败","10110043"),
    insertShipPlanFail("插入船计划失败","10110044"),
    checkBillFail("复核失败","10110044"),
    ycIdsIsNull("云仓id为空","10110045"),
    billIsOut("货物正在出库不可终止","10110046"),
    rejectBillFail("驳回失败","10110047"),
    stopBillFail("终止失败","10110048"),
    DELAY_BILL_FAIL("延期失败","10110049"),
    houseIsNotExist("仓库信息不存在","10110050"),

    BILL_IS_COMPLETE_OP("此单据已完成当前操作，无需重试","10110051"),
    TRUCK_PRICE_IS_NULL("驾驶员付费时，装车费用不能为空","10110052"),
    TRANS_TYPE_IS_NULL("提货车辆类型为空","10110053"),
    TRANS_TYPE_IS_ILLEGAL("提货车辆类型不合法","10110054"),
    TRUCK_IS_NULL("非指定承运商时，车辆信息不能为空","10110055"),
    TRUCK_SIZE_OVER("车辆数量超过最大可提交数量","10110056"),
    TRUCK_IS_REPEAT("车辆信息重复","10110057"),
    TRUCK_NAME_IS_NULL("车名为空","10110058"),
    TRUCK_NAME_IS_ILLEGAL("车名不合法","10110059"),
    TRUCK_PRICE_IS_ILLEGAL("提单类型为车发时，是否驾驶员付费不能为空","10110060"),
    TRANSFER_OWNER_ID_IS_NULL("转入方企业id不能为空","10110061"),
    TRANSFER_OWNER_NAME_IS_NULL("转入方企业名称不能为空","10110062"),
    CHANGE_LOCATION_FAIL("换罐失败","10110063"),
    TRUCK_LIST_IS_NULL("车船信息为空","101K10064"),
    TRUCK_IS_OUT("车辆已出库不能变更","10110065"),
    CHANGE_TRANSPORT_FAIL("变更失败","10110066"),

    BILLNO_IS_NOT_BELONG_OWNER("该提单不属于该用户", "10110067"),
    BILLTYPE_IS_NOT_VALID("提单类型不合法", "10110068"),
    BILLNO_LIST_IS_NULL("提单列表参数不能为空", "10110069"),
    OWNERBILLNO_LIST_IS_NULL("客户提单列表参数不能为空", "10110070"),
    TRUCK_IS_ILLEGAL("车名不合法","10110071"),
    OWNERBILLNO_IS_NULL("客户提单号不能为空", "10110072"),
    CKBILLNO_IS_NULL("出库明细提单号不能为空", "10110073"),

    EXIST_SHIP_PLAN_IS_ILLEGAL("已存在的船计划不合法", "10110074"),
    SHIP_PLAN_NO_IS_NULL("船计划单号为空", "10110075"),
    BILL_STATS_IS_ILLEGAL("单据状态不合法", "10110076"),
    SHIP_PLAN_IS_NULL("船计划不存在", "10110077"),


    //    dongZhou code 08 代表dongZhou
    ORDER_TYPE_IS_NULL("单号类型为空", "10080001"),
    BILL_NO_IS_ENTITY("提单号不能为空", "10080002"),
    BILL_TRUCK_LIST_IS_NULL("车辆信息不能为空", "10080003"),
    BILL_TRUCK_TYPE_IS_NULL("车辆类型不能为空", "10080004"),
    BILL_AUDITOR_IS_NULL("审核人不能为空", "10080005"),
    BILL_CREATOR_IS_NULL("创建人不能为空", "10080006"),
    BILL_EXPIRE_DATE_IS_NULL("出仓委托单的有效期不能为空", "10080007"),
    BILL_GOODS_IS_NULL("货品不能为空", "10080008"),
    BILL_OWNER_IS_NULL("货主不能为空", "10080009"),
    BILL_QUANTITY_IS_NULL("货量不能为空，且必须大于零", "10080010"),
    BILL_TRUCK_LIST_INVALID("多对多模式下，车辆数据不合法","10080011"),
    BILL_LOCATION_LIST_INVALID("罐号不能为空","10080012"),
    PARAM_IS_NULL("参数为空","10080013"),
    OWNER_BILL_NO_EXIST_TYPE_IS_NOT_VALID("查询客户提单号是否存在，必须类型值为空或不合法","10080014"),
    OWNER_BILL_NO_EXIST_OWNER_PLAN_BILLNO_IS_NOT_VALID("查询客户提单号是否存在，客户提单号为空或不合法","10080015"),
    OWNER_BILL_NO_EXIST_OWNER_BILLNO_IS_NOT_VALID("查询客户提单号是否存在，小单客户提单号为空或不合法","10080016"),
    QUERY_NO_DATA_ERROR("查询成功无数据", "10080013"),


    DONG_ZHOU_FAIL("请求ERP系统错误，请稍后再试","089999999"),
    ;

    private String resultMsg;
    private String resultCode;

    ResultCodeEnum(String resultMsg, String resultCode) {
        this.resultCode=resultCode;
        this.resultMsg =resultMsg;
    }
    public String getResultCode() {
        return resultCode;
    }
    public String getResultMsg() {
        return resultMsg;
    }
}
