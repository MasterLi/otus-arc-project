public class BillingServiceResponse implements Serializable {

    private static final long serialVersionUID = -5581182632959459815L;

    @ApiModelProperty(value = "Признак успешности выполнения запроса")
    protected Boolean isSuccess;

    @ApiModelProperty(value = "Текст сообщения или ошибки")
    protected String message;
}