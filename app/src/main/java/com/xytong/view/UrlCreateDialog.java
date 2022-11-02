package com.xytong.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.xytong.dao.SettingDao;
import com.xytong.databinding.DialogChageUrlBinding;

import java.lang.reflect.Field;
import java.util.Objects;


public class UrlCreateDialog extends DialogFragment {
    DialogChageUrlBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogChageUrlBinding.inflate(getLayoutInflater());
        binding.urlRe.setText(SettingDao.getUrl(requireContext(), SettingDao.RE_URL_NAME, SettingDao.RE_URL_RES));
        binding.urlSh.setText(SettingDao.getUrl(requireContext(), SettingDao.SH_URL_NAME, SettingDao.SH_URL_RES));
        binding.urlComment.setText(SettingDao.getUrl(requireContext(), SettingDao.COMMENT_URL_NAME, SettingDao.COMMENT_URL_RES));
        binding.urlForum.setText(SettingDao.getUrl(requireContext(), SettingDao.FORUM_URL_NAME, SettingDao.FORUM_URL_RES));
        binding.urlAccess.setText(SettingDao.getUrl(requireContext(), SettingDao.ACCESS_URL_NAME, SettingDao.ACCESS_URL_RES));
        binding.urlUser.setText(SettingDao.getUrl(requireContext(), SettingDao.USER_URL_NAME, SettingDao.USER_URL_RES));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("设置获取信息url")
                .setView(binding.getRoot())
                .setNeutralButton("重置", (dialog, id) -> {
                    try {
                        Field field = Objects.requireNonNull(dialog.getClass().getSuperclass()).getDeclaredField("mShowing");//反射欺骗系统
                        field.setAccessible(true);
                        field.set(dialog, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    binding.urlRe.setText(SettingDao.RE_URL_RES);
                    binding.urlSh.setText(SettingDao.SH_URL_RES);
                    binding.urlComment.setText(SettingDao.COMMENT_URL_RES);
                    binding.urlForum.setText(SettingDao.FORUM_URL_RES);
                    binding.urlAccess.setText(SettingDao.ACCESS_URL_RES);
                    binding.urlUser.setText(SettingDao.USER_URL_RES);
                })
                .setPositiveButton("保存", (dialog, id) -> {
                    SettingDao.setUrl(requireContext(), SettingDao.COMMENT_URL_NAME, String.valueOf(binding.urlComment.getText()));
                    SettingDao.setUrl(requireContext(), SettingDao.RE_URL_NAME, String.valueOf(binding.urlRe.getText()));
                    SettingDao.setUrl(requireContext(), SettingDao.FORUM_URL_NAME, String.valueOf(binding.urlForum.getText()));
                    SettingDao.setUrl(requireContext(), SettingDao.SH_URL_NAME, String.valueOf(binding.urlSh.getText()));
                    SettingDao.setUrl(requireContext(), SettingDao.ACCESS_URL_NAME, String.valueOf(binding.urlAccess.getText()));
                    SettingDao.setUrl(requireContext(), SettingDao.USER_URL_NAME, String.valueOf(binding.urlUser.getText()));
                    try {
                        Field field = Objects.requireNonNull(dialog.getClass().getSuperclass()).getDeclaredField("mShowing");
                        field.setAccessible(true);
                        field.set(dialog, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("取消", (dialog, id) -> {
                    try {
                        Field field = Objects.requireNonNull(dialog.getClass().getSuperclass()).getDeclaredField("mShowing");
                        field.setAccessible(true);
                        field.set(dialog, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        return builder.create();
    }
}
