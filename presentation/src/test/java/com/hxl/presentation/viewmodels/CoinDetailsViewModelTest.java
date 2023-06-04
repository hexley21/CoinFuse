package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.PresentationTestConstants.ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hxl.domain.interactors.coins.IsCoinBookmarked;
import com.hxl.presentation.livedata.DataState;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class CoinDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private IsCoinBookmarked isCoinBookmarked;

    @InjectMocks
    private CoinDetailsViewModel viewModel;

    private final Exception exception = new Exception("Fake error");

    @SuppressLint("CheckResult")
    @BeforeClass
    public static void classSetUp() {
        mockStatic(Log.class);
        doAnswer(invocation -> {
            String tag = invocation.getArgument(0);
            String message = invocation.getArgument(1);
            Throwable throwable = invocation.getArgument(2);
            System.out.println(tag + ": " + message);
            throwable.printStackTrace();
            return 0;
        }).when(Log.class);
        Log.e(anyString(), anyString(), any(Throwable.class));

        Scheduler immediateScheduler = Schedulers.trampoline();
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediateScheduler);
    }

    @Test
    public void fetchBookmarkStateSuccess() {
        // Arrange
        when(isCoinBookmarked.invoke(anyString())).thenReturn(Single.just(true));
        // Act
        viewModel.fetchBookmarkState(ID);
        // Assert
        assertNotNull(viewModel.getCurrentBookmarkState().getValue());
        assertEquals(DataState.SUCCESS, viewModel.getCurrentBookmarkState().getValue().getState());
        assertTrue(viewModel.getCurrentBookmarkState().getValue().getData());

        verify(isCoinBookmarked).invoke(ID);
    }

    @Test
    public void fetchBookmarkStateError() {
        // Arrange
        when(isCoinBookmarked.invoke(anyString())).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchBookmarkState(ID);
        // Assert
        assertNotNull(viewModel.getCurrentBookmarkState().getValue());
        assertEquals(DataState.ERROR, viewModel.getCurrentBookmarkState().getValue().getState());
        assertEquals(exception, viewModel.getCurrentBookmarkState().getValue().getError());

        verify(isCoinBookmarked).invoke(ID);
    }

}