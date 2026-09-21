package com.personal.godou.ui.vocab;

import com.personal.godou.data.repository.VocabRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class VocabViewModel_Factory implements Factory<VocabViewModel> {
  private final Provider<VocabRepository> repositoryProvider;

  private VocabViewModel_Factory(Provider<VocabRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public VocabViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static VocabViewModel_Factory create(Provider<VocabRepository> repositoryProvider) {
    return new VocabViewModel_Factory(repositoryProvider);
  }

  public static VocabViewModel newInstance(VocabRepository repository) {
    return new VocabViewModel(repository);
  }
}
