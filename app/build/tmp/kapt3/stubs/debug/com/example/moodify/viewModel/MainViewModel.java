package com.example.moodify.viewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/moodify/viewModel/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/example/moodify/Repository/MoodifyRepository;", "(Lcom/example/moodify/Repository/MoodifyRepository;)V", "getSession", "Landroidx/lifecycle/LiveData;", "Lcom/example/moodify/retrofit/UserModel;", "logout", "", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.example.moodify.Repository.MoodifyRepository repository = null;
    
    public MainViewModel(@org.jetbrains.annotations.NotNull
    com.example.moodify.Repository.MoodifyRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<com.example.moodify.retrofit.UserModel> getSession() {
        return null;
    }
    
    public final void logout() {
    }
}