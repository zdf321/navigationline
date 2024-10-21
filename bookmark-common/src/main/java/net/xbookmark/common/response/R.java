package net.xbookmark.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class R<T> implements Serializable {

  @ApiModelProperty(value = "接口返回状态，1-成功 2-失败 3-未登录")
  private String status = StatusCode.SUCCESS;

  @ApiModelProperty(value = "接口返回的信息")
  private String msg = "请求成功";

  @ApiModelProperty(value = "返回值")
  private T data = null;

  public static <T> R<T> success(T data) {
    R<T> r = new R<>();
    r.setData(data);
    return r;
  }

  public static R error(String msg) {
    return error(StatusCode.FAILED, msg);
  }

  public static R error(String status, String msg) {
    R r = new R<>();
    r.setMsg(msg);
    r.setStatus(status);
    return r;
  }

  public R() {}

  public R(T data) {
    this.data = data;
  }

  public R(T data, String message) {
    this.msg = message;
    this.data = data;
  }

  public R(String code, String message) {
    this.status = code;
    this.msg = message;
  }

  public R(T data, String code, String message) {
    this.status = code;
    this.msg = message;
    this.data = data;
  }
}
