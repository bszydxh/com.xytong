package com.xytong.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.xytong.data.SharedPreferences.SettingSP;
import com.xytong.databinding.DialogChageUrlBinding;

import java.lang.reflect.Field;
import java.util.Objects;

public class UrlCreateDialog extends DialogFragment {
    DialogChageUrlBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogChageUrlBinding.inflate(getLayoutInflater());
        binding.urlRe.setText(SettingSP.getReUrl(requireContext()));
        binding.urlSh.setText(SettingSP.getShUrl(requireContext()));
        binding.urlComment.setText(SettingSP.getCommentUrl(requireContext()));
        binding.urlForum.setText(SettingSP.getForumUrl(requireContext()));
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
                    SettingSP.setCommentUrl(requireContext(), "");
                    SettingSP.setReUrl(requireContext(), "");
                    SettingSP.setForumUrl(requireContext(), "");
                    SettingSP.setShUrl(requireContext(), "");
                    binding.urlRe.setText(SettingSP.getReUrl(requireContext()));
                    binding.urlSh.setText(SettingSP.getShUrl(requireContext()));
                    binding.urlComment.setText(SettingSP.getCommentUrl(requireContext()));
                    binding.urlForum.setText(SettingSP.getForumUrl(requireContext()));
                })
                .setPositiveButton("保存", (dialog, id) -> {
                    SettingSP.setCommentUrl(requireContext(), String.valueOf(binding.urlComment.getText()));
                    SettingSP.setReUrl(requireContext(), String.valueOf(binding.urlRe.getText()));
                    SettingSP.setForumUrl(requireContext(), String.valueOf(binding.urlForum.getText()));
                    SettingSP.setShUrl(requireContext(), String.valueOf(binding.urlSh.getText()));
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
