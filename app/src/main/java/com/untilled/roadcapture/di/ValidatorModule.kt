package com.untilled.roadcapture.di

import androidx.fragment.app.Fragment
import com.mobsandgeeks.saripaar.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton

@InstallIn(FragmentComponent::class)
@Module
class ValidatorModule {

    @Provides
    fun provideValidator(fragment: Fragment): Validator {
        return Validator(fragment)
    }
}