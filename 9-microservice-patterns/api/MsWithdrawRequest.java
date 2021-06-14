public class MsWithdrawRequest implements Serializable {

    private static final long serialVersionUID = 3138924099980406500L;

    @ApiModelProperty(value = "Пользователь")
    protected String userName;

    @ApiModelProperty(value = "Сумма списания")
    protected Double sum;

    @ApiModelProperty(value = "Наименование платежа")
    protected String comment;
}