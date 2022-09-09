package com.xytong.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.xytong.R;
import com.xytong.data.sharedPreferences.SettingSP;
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
        binding.urlAccess.setText(SettingSP.getAccessUrl(requireContext()));
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
                })
                .setPositiveButton("保存", (dialog, id) -> {
                    SettingSP.setCommentUrl(requireContext(), String.valueOf(binding.urlComment.getText()));
                    SettingSP.setReUrl(requireContext(), String.valueOf(binding.urlRe.getText()));
                    SettingSP.setForumUrl(requireContext(), String.valueOf(binding.urlForum.getText()));
                    SettingSP.setShUrl(requireContext(), String.valueOf(binding.urlSh.getText()));
                    SettingSP.setAccessUrl(requireContext(), String.valueOf(binding.urlAccess.getText()));
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
