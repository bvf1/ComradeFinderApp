package is.hbv2.ComradeFinderApp.Network;

public interface NetworkCallback<T> {

    void onSuccess(T result);

    void onFailure(String errorString);
}
