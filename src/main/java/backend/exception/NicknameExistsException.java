package backend.exception;

public class NicknameExistsException extends RuntimeException {
  public NicknameExistsException() {
    super("이미 존재하는 닉네임입니다.");
  }
}