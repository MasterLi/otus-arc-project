public class MsSendMailRequest implements Serializable {

    private static final long serialVersionUID = 3138924099980406500L;

    @ApiModelProperty(value = "Пользователь")
    protected String userName;

    @ApiModelProperty(value = "Отправитель")
    protected String sender;

    @ApiModelProperty(value = "Получатели")
    protected String recipient;

    @ApiModelProperty(value = "Тема")
    protected String subject;

    @ApiModelProperty(value = "Тело")
    protected String body;
}