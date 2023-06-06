package com.hxl.presentation.viewmodels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ProfitCalculatorViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ProfitCalculatorViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ProfitCalculatorViewModel();
    }

    @Test
    public void calculateProfit() {
        // Arrange
        viewModel.setBuyPriceField(1.0D);
        viewModel.setInvestmentField(6.0D);
        viewModel.setSellPriceField(2.0D);
        // Act
        final MutableLiveData<Double> getCurrentProfit = viewModel.getCurrentProfit();
        final MutableLiveData<Double> getCurrentExit = viewModel.getCurrentExit();
        final MutableLiveData<Double> getCurrentInvestment = viewModel.getCurrentInvestment();
        // Assert
        assertNotNull(getCurrentProfit.getValue());
        assertNotNull(getCurrentExit.getValue());
        assertNotNull(getCurrentInvestment.getValue());

        assertEquals(6.0D, getCurrentProfit.getValue(), 0.0);
        assertEquals(6.0D, getCurrentInvestment.getValue(), 0.0);
        assertEquals(12.0D, getCurrentExit.getValue(), 0.0);
        assertEquals(1.0D, viewModel.getChange(), 0.0D);

        // Arrange #2
        viewModel.setInvestmentFeeField(2.0D);

        // Assert #2
        assertEquals(4.0D, getCurrentProfit.getValue(), 0.0);
        assertEquals(8.0D, getCurrentInvestment.getValue(), 0.0);
        assertEquals(12.0D, getCurrentExit.getValue(), 0.0);
        assertEquals(0.66, viewModel.getChange(), 0.1);

        // Arrange #3
        viewModel.setExitFeeField(2.0D);

        // Assert #3
        assertEquals(2.0D, getCurrentProfit.getValue(), 0.0);
        assertEquals(8.0D, getCurrentInvestment.getValue(), 0.0);
        assertEquals(10.0D, getCurrentExit.getValue(), 0.0);
        assertEquals(0.33, viewModel.getChange(), 0.1);

    }
}