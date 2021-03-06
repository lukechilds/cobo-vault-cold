/*
 * Copyright (c) 2020 Cobo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * in the file COPYING.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cobo.cold.ui.fragment.unlock;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModelProviders;

import com.cobo.cold.R;
import com.cobo.cold.Utilities;
import com.cobo.cold.databinding.VerifyMnemonicBinding;
import com.cobo.cold.ui.fragment.BaseFragment;
import com.cobo.cold.util.Keyboard;
import com.cobo.cold.viewmodel.SharedDataViewModel;

import java.util.Objects;

import static com.cobo.cold.ui.fragment.Constants.IS_FORCE;
import static com.cobo.cold.ui.fragment.Constants.KEY_NAV_ID;
import static com.cobo.cold.ui.fragment.Constants.KEY_TITLE;
import static com.cobo.cold.ui.fragment.setup.MnemonicInputFragment.isValidWord;
import static com.cobo.cold.ui.fragment.setup.SetPasswordFragment.MNEMONIC;

public class VerifyMnemonicFragment extends BaseFragment<VerifyMnemonicBinding> {

    public static final String TAG = "VerifyMnemonicFragment";
    private int navId;

    @Override
    protected int setView() {
        return R.layout.verify_mnemonic;
    }

    @Override
    protected void init(View view) {
        Bundle data = Objects.requireNonNull(getArguments());
        navId = data.getInt(KEY_NAV_ID);
        if (data.getBoolean(IS_FORCE)) {
            mBinding.toolbar.setNavigationIcon(new ColorDrawable(Color.TRANSPARENT));
        } else {
            mBinding.toolbar.setNavigationOnClickListener(v -> navigateUp());
        }
        String title = data.getString(KEY_TITLE);
        title = TextUtils.isEmpty(title) ? getString(R.string.verify_mnemonic) : title;
        mBinding.toolbarTitle.setText(title);
        mBinding.table.setMnemonicNumber(Utilities.getMnemonicCount(mActivity));
        mBinding.setCount(Utilities.getMnemonicCount(mActivity));
        mBinding.verifyMnemonic.setOnClickListener(this::validateMnemonic);
        mBinding.table.getWordsList().forEach(o -> o.addOnPropertyChangedCallback(
                new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable sender, int propertyId) {
                        if (mBinding.table.getWordsList()
                                .stream()
                                .allMatch(s -> isValidWord(s.get()))) {
                            mBinding.verifyMnemonic.setEnabled(true);
                        } else {
                            mBinding.verifyMnemonic.setEnabled(false);
                        }
                    }
                }));
        Keyboard.show(mActivity, mBinding.table);
    }

    private void validateMnemonic(View view) {
        String mnemonic = mBinding.table.getWordsList()
                .stream()
                .map(ObservableField::get)
                .reduce((s1, s2) -> s1 + " " + s2)
                .orElse("");

        SharedDataViewModel model = ViewModelProviders.of(mActivity).get(SharedDataViewModel.class);
        boolean match = model.verifyMnemonic(mnemonic);
        if (match) {
            mBinding.table.getWordsList().clear();
            navigate(navId, Bundle.forPair(MNEMONIC, mnemonic));
        } else {
            Utilities.alert(mActivity, getString(R.string.hint),
                    getString(R.string.wrong_mnemonic),
                    getString(R.string.confirm), null);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onPause() {
        super.onPause();
        Keyboard.hide(mActivity, getView());
    }
}
