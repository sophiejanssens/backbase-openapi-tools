package com.backbase.oss.boat.diff.model.schema;

import com.backbase.oss.boat.diff.model.Changed;
import com.backbase.oss.boat.diff.model.DiffContext;
import com.backbase.oss.boat.diff.model.DiffResult;
import java.util.Objects;
import java.util.Optional;

public class ChangedReadOnly implements Changed {
  private final DiffContext context;
  private final boolean oldValue;
  private final boolean newValue;

  public ChangedReadOnly(Boolean oldValue, Boolean newValue, DiffContext context) {
    this.context = context;
    this.oldValue = Optional.ofNullable(oldValue).orElse(false);
    this.newValue = Optional.ofNullable(newValue).orElse(false);
  }

  @Override
  public DiffResult isChanged() {
    if (Objects.equals(oldValue, newValue)) {
      return DiffResult.NO_CHANGES;
    }
    if (context.isResponse()) {
      return DiffResult.COMPATIBLE;
    }
    if (context.isRequest()) {
      if (Boolean.TRUE.equals(newValue)) {
        return DiffResult.INCOMPATIBLE;
      } else {
        return context.isRequired().booleanValue() ? DiffResult.INCOMPATIBLE : DiffResult.COMPATIBLE;
      }
    }
    return DiffResult.UNKNOWN;
  }
}
