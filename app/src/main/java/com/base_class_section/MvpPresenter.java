
package com.base_class_section;

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    // void handleApiError(ANError error);

    void setUserAsLoggedOut();
}
