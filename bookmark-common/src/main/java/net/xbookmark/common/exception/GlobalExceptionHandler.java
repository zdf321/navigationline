package net.xbookmark.common.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.response.R;
import net.xbookmark.common.response.StatusCode;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

  @ExceptionHandler
  @ResponseBody
  public R handle(Exception exception) {
    R result = new R();
    if (exception instanceof BusinessException) {
      BusinessException businessException = (BusinessException) exception;
      result.setStatus(businessException.getCode());
      result.setMsg(businessException.getMessage());

    } else if (exception instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException e = (MethodArgumentNotValidException) exception;
      List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
      List<String> codes = Lists.newArrayList();
      String msg = "";
      if (CollectionUtils.isNotEmpty(fieldErrors)) {
        Iterator var15 = fieldErrors.iterator();
        while (var15.hasNext()) {
          FieldError fe = (FieldError) var15.next();
          codes.add(fe.getDefaultMessage());
        }
        Collections.sort(codes);
        msg = codes.get(0);
      }
      result.setStatus(StatusCode.FAILED);
      result.setMsg(msg);
    } else if (exception instanceof BindException) {
      BindException e = (BindException) exception;
      List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
      List<String> codes = Lists.newArrayList();
      String msg = "";
      if (CollectionUtils.isNotEmpty(fieldErrors)) {
        Iterator var15 = fieldErrors.iterator();
        while (var15.hasNext()) {
          FieldError fe = (FieldError) var15.next();
          codes.add(fe.getDefaultMessage());
        }
        Collections.sort(codes);
        msg = codes.get(0);
      }
      result.setStatus(StatusCode.FAILED);
      result.setMsg(msg);
    } else if (exception instanceof MaxUploadSizeExceededException) {
      result.setStatus(StatusCode.FAILED);
      result.setMsg("文件过大");
    } else if (exception instanceof IllegalArgumentException) {
      result.setStatus(StatusCode.FAILED);
      result.setMsg("参数非法");
    } else if (exception instanceof MissingServletRequestParameterException) {
      MissingServletRequestParameterException missingServletRequestParameterException =
          (MissingServletRequestParameterException) exception;
      result.setStatus(StatusCode.FAILED);
      result.setMsg(
          String.format("参数:%s必填", missingServletRequestParameterException.getParameterName()));
    } else if (exception.getCause() instanceof InvalidFormatException) {
      result.setStatus(StatusCode.FAILED);
      result.setMsg("字段类型错误");
    } else {
      result.setStatus(StatusCode.FAILED);
      result.setMsg(exception.getMessage());
    }
    log.error("", exception);

    return result;
  }
}
