package com.xytong.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.xytong.R;
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
        binding.urlRe.setText(SettingDao.getReUrl(requireContext()));
        binding.urlSh.setText(SettingDao.getShUrl(requireContext()));
        binding.urlComment.setText(SettingDao.getCommentUrl(requireContext()));
        binding.urlForum.setText(SettingDao.getForumUrl(requireContext()));
        binding.urlAccess.setText(SettingDao.getAccessUrl(requireContext()));
        binding.urlUser.setText(SettingDao.getUserUrl(requireContext()));
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
                    Context context = requireContext();
                    binding.urlRe.setText(context.getString(R.string.re_url));
                    binding.urlSh.setText(context.getString(R.string.sh_url));
                    binding.urlComment.setText(context.getString(R.string.comment_url));
                    binding.urlForum.setText(context.getString(R.string.forum_url));
                    binding.urlAccess.setText(context.getString(R.string.access_url));
                    binding.urlUser.setText(context.getString(R.string.user_url));
                })
                .setPositiveButton("保存", (dialog, id) -> {
                    SettingDao.setCommentUrl(requireContext(), String.valueOf(binding.urlComment.getText()));
                    SettingDao.setReUrl(requireContext(), String.valueOf(binding.urlRe.getText()));
                    SettingDao.setForumUrl(requireContext(), String.valueOf(binding.urlForum.getText()));
                    SettingDao.setShUrl(requireContext(), String.valueOf(binding.urlSh.getText()));
                    SettingDao.setAccessUrl(requireContext(), String.valueOf(binding.urlAccess.getText()));
                    SettingDao.setUserUrl(requireContext(), String.valueOf(binding.urlUser.getText()));
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
