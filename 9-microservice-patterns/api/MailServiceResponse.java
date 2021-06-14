public class MailServiceResponse implements Serializable {

   private static final long serialVersionUID = -990585122334326439L;

   @ApiModelProperty(value = "Признак успешности выполнения запроса")
   protected Boolean isSuccess;

   @ApiModelProperty(value = "Текст сообщения или ошибки")
   protected String message;
}