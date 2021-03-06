package web.school.demo.comment.dto;

public class BaseResultFactory {
    public static AbstractBaseResult build(String self){
        return new SuccessResult(self);
    }

    public static AbstractBaseResult build(String self,int next,int last){
        return new SuccessResult(self,next,last);
    }

    public static AbstractBaseResult build(int code,String msg,String detail){
        return new ErrorResult(code,msg,detail);
    }
    public static AbstractBaseResult build(int code,String msg){
        return new ErrorResult(code,msg);
    }
    public static AbstractBaseResult build(Object data){
        return new SuccessResult(data);
    }
}
