package xyz.pixelatedw.mineminenomi.api.util;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class Result {
   private ResultType result;
   private @Nullable Component message;

   public Result(ResultType result, @Nullable Component message) {
      this.result = result;
      this.message = message;
   }

   public ResultType getResult() {
      return this.result;
   }

   public @Nullable Component getMessage() {
      return this.message;
   }

   public boolean isSuccess() {
      return this.getResult() == Result.ResultType.SUCCESS;
   }

   public boolean isSuccessOr(ISuccessCallback callback) {
      if (this.isSuccess()) {
         return true;
      } else {
         callback.callback(this.getMessage());
         return false;
      }
   }

   public boolean isFail() {
      return this.getResult() == Result.ResultType.FAIL;
   }

   public Result and(Result result2) {
      return this.isSuccess() && result2.isSuccess() ? success() : (this.isFail() ? fail(this.getMessage()) : fail(result2.getMessage()));
   }

   public Result or(Result result2) {
      return !this.isSuccess() && !result2.isSuccess() ? (this.isFail() ? fail(this.getMessage()) : fail(result2.getMessage())) : success();
   }

   public static Result success() {
      return new Result(Result.ResultType.SUCCESS, (Component)null);
   }

   public static Result fail(@Nullable Component message) {
      return new Result(Result.ResultType.FAIL, message);
   }

   public Result inverse() {
      if (this.getResult() == Result.ResultType.SUCCESS) {
         this.result = Result.ResultType.FAIL;
      } else {
         this.result = Result.ResultType.SUCCESS;
      }

      return this;
   }

   public static enum ResultType {
      SUCCESS,
      FAIL;

      // $FF: synthetic method
      private static ResultType[] $values() {
         return new ResultType[]{SUCCESS, FAIL};
      }
   }

   @FunctionalInterface
   public interface IFailCallback<T> {
      void callback(T var1);
   }

   @FunctionalInterface
   public interface ISuccessCallback {
      void callback(Component var1);
   }
}
